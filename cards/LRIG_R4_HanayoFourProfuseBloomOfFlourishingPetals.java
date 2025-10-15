package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_R4_HanayoFourProfuseBloomOfFlourishingPetals extends Card {

    public LRIG_R4_HanayoFourProfuseBloomOfFlourishingPetals()
    {
        setImageSets("WX24-P4-014", "WX24-P4-014U");

        setOriginalName("閃華繚乱　花代・肆");
        setAltNames("センカリョウランハナヨヨン Senkaryouran Hanayo Yon");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜花代＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたの手札が４枚より少ない場合、その差の分だけカードを引く。\n" +
                "@A $G1 @[@|アンビション|@]@ %R0：&E４枚以上@0以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のライフクロス１枚をクラッシュする。\n" +
                "$$2対戦相手のライフクロスが０枚の場合、対戦相手は自分のルリグデッキからカード１枚をルリグトラッシュに置く。"
        );

        setName("en", "Hanayo Four, Profuse Bloom of Flourishing Petals");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Hanayo>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: If there are 4 or less cards in your hand, draw a number of cards equal to the difference.\n" +
                "@A $G1 @[@|Ambition|@]@ %R0: &E4 or more@0 @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Crush 1 of your opponent's life cloth.\n" +
                "$$2 If your opponent has 0 life cloth, your opponent puts 1 card from their LRIG deck into the trash."
        );

		setName("zh_simplified", "闪华缭乱 花代·肆");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<花代>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:你的手牌比4张少的场合，抽那个差值的牌。\n" +
                "@A $G1 :热望%R0&E4张以上@0从以下的2种选1种。\n" +
                "$$1 对战对手的生命护甲1张击溃。\n" +
                "$$2 对战对手的生命护甲在0张的场合，对战对手从自己的分身牌组把1张牌放置到分身废弃区。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.HANAYO).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Ambition");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            int count = getHandCount(getOwner());
            if(count <= 4)
            {
                draw(4-count);
            }
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                if(playerChoiceMode() == 1)
                {
                    crush(getOpponent());
                } else {
                    CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.TRASH).own().fromLocation(CardLocation.DECK_LRIG).anyCard()).get();
                    trash(cardIndex);
                }
            }
        }
    }
}
