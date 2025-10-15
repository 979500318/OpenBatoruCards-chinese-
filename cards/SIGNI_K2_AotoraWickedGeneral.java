package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_K2_AotoraWickedGeneral extends Card {

    public SIGNI_K2_AotoraWickedGeneral()
    {
        setImageSets("WXDi-P15-098");

        setOriginalName("凶将　アオトラ");
        setAltNames("キョウショウアオトラ Kyoushou Aotora");
        setDescription("jp",
                "@C：あなたの黒のシグニは@>@U：このシグニがアタックしたとき、対戦相手のデッキの一番上のカードをトラッシュに置く。@@を得る。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Aotora, Doomed General");
        setDescription("en",
                "@C: Black SIGNI on your field gain@>@U: Whenever this SIGNI attacks, put the top card of your opponent's deck into their trash.@@" +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Aotora, Wicked General");
        setDescription("en_fan",
                "@C: All of your black SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, put the top card of your opponent's deck into the trash.@@" +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶将 蓝虎");
        setDescription("zh_simplified", 
                "@C :你的黑色的精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的牌组最上面的牌放置到废弃区。@@" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().SIGNI().withColor(CardColor.BLACK), new AbilityGainModifier(this::onConstEffModGetSample));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().millDeck(getOpponent(), 1);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
