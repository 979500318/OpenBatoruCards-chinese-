package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K3_AnnaMirageWickedDevilPrincess extends Card {

    public SIGNI_K3_AnnaMirageWickedDevilPrincess()
    {
        setImageSets("WX24-P1-048");
        setLinkedImageSets("WX24-P1-015");

        setOriginalName("凶魔姫　アンナ・ミラージュ");
        setAltNames("キョウマキアンナミラージュ Kyoumaki Anna Miraaju");
        setDescription("jp",
                "@U：あなたのシグニ１体がバニッシュされたとき、対戦相手のデッキの一番上のカードをトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《挟界の閻魔　ウリス》がいる場合、対戦相手は自分のシグニ１体を選びバニッシュする。\n" +
                "@E：あなたの他の＜悪魔＞のシグニ２体を場からトラッシュに置かないかぎり、このシグニをダウンする。"
        );

        setName("en", "Anna Mirage, Wicked Devil Princess");
        setDescription("en",
                "@U: Whenever 1 of your SIGNI is banished, put the top card of your opponent's deck into the trash.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Urith, Barrier Enma\", your opponent chooses 1 of their SIGNI, and banishes it.\n" +
                "@E: Down this SIGNI unless you put 2 of your other <<Devil>> SIGNI from the field into the trash."
        );

		setName("zh_simplified", "凶魔姬 安娜·蜃影");
        setDescription("zh_simplified", 
                "@U :当你的精灵1只被破坏时，对战对手的牌组最上面的牌放置到废弃区。（当这只精灵被破坏时，也发动）\n" +
                "@U :当这只精灵攻击时，你的场上有《挟界の閻魔　ウリス》的场合，对战对手选自己的精灵1只破坏。\n" +
                "@E :如果不把你的其他的<<悪魔>>精灵2只从场上放置到废弃区，那么这只精灵横置。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            millDeck(getOpponent(), 1);
        }
        
        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("挟界の閻魔　ウリス"))
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BANISH).own().SIGNI()).get();
                banish(cardIndex);
            }
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DEVIL).except(getCardIndex()));
            if(trash(data) != 2)
            {
                down();
            }
        }
    }
}
