package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_SvarogPhantomWarDragonPrincess extends Card {

    public SIGNI_R3_SvarogPhantomWarDragonPrincess()
    {
        setImageSets("WX24-P2-050");
        setLinkedImageSets("WX24-P2-018");

        setOriginalName("幻闘竜姫　スヴァローグ");
        setAltNames("ゲントウリュウキスヴァローグ Gentouryuuki Suvaroogu");
        setDescription("jp",
                "@U：アタックフェイズの間、対戦相手のエナゾーンにカード１枚が置かれたとき、対戦相手のエナゾーンにカードが３枚以上あり、このターンにこの能力でカードをトラッシュに置いていない場合、そのカードをトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《熾炎舞　遊月・参》がいる場合、対戦相手のパワー12000以下のシグニ１体を対象とし、あなたのエナゾーンから＜龍獣＞のシグニ２枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Svarog, Phantom War Dragon Princess");
        setDescription("en",
                "@U: During the attack phase, whenever a card is put into your opponent's ener zone, if there are 3 or more cards in your opponent's ener zone and you haven't put a card into the trash with this ability this turn, put that card into the trash.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Yuzuki Three, Incandescent Dance\", target 1 of your opponent's SIGNI with power 12000 or less, and you may put 2 <<Dragon Beast>> SIGNI from your ener zone into the trash. If you do, banish it."
        );

		setName("zh_simplified", "幻斗龙姬 斯瓦罗格");
        setDescription("zh_simplified", 
                "@U :攻击阶段期间，当对战对手的能量区有1张牌放置时，对战对手的能量区的牌在3张以上且，这个回合这个能力没有把牌放置到废弃区的场合，那张牌放置到废弃区。\n" +
                "@U :当这只精灵攻击时，你的场上有《熾炎舞　遊月・参》的场合，对战对手的力量12000以下的精灵1只作为对象，可以从你的能量区把<<龍獣>>精灵2张放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ENER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEffCond1(CardIndex caller)
        {
            return GamePhase.isAttackPhase(getCurrentPhase()) && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getEnerCount(getOpponent()) >= 3 && GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.TRASH && event.getSourceAbility() == getAbility()) == 0)
            {
                trash(caller);
            }
        }

        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("熾炎舞　遊月・参"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                
                if(target != null)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST).fromEner());
                    if(trash(data) == 2)
                    {
                        banish(target);
                    }
                }
            }
        }
    }
}
