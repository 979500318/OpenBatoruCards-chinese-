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

public final class LRIG_B4_EldoraXMarkIVFINAL extends Card {

    public LRIG_B4_EldoraXMarkIVFINAL()
    {
        setImageSets("WX24-P4-019", "WX24-P4-019U");

        setOriginalName("エルドラ×マークⅣ　FINAL");
        setAltNames("エルドラマークフォーファイナル Erudora Maaku Foo Fainaru EldoraxMark IV Eldora x Mark IV");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜エルドラ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：カードを２枚引く。\n" +
                "@A $G1 @[@|リクエスト|@]@ %B0：&E４枚以上@0以下の２つから１つを選ぶ。\n" +
                "$$1あなたのライフクロス１枚を手札に加える。そうした場合、あなたの手札からカードを１枚ライフクロスに加える。\n" +
                "$$2あなたのライフクロスが０枚の場合、あなたのデッキをシャッフルし一番上のカードをライフクロスに加える。"
        );

        setName("en", "Eldora×Mark IV FINAL");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Eldora>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Draw 2 cards.\n" +
                "@A $G1 @[@|Request|@]@ %B0: &E4 or more@0 @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Add 1 of your life cloth to hand. If you do, add 1 card from your hand to life cloth.\n" +
                "$$2 If you have 0 life cloth, shuffle your deck, and add the top card of your deck to life cloth."
        );

		setName("zh_simplified", "艾尔德拉×IV式 FINAL");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<エルドラ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:抽2张牌。\n" +
                "@A $G1 :请求%B0&E4张以上@0从以下的2种选1种。\n" +
                "$$1 你的生命护甲1张加入手牌。这样做的场合，从你的手牌把1张牌加入生命护甲。\n" +
                "$$2 你的生命护甲在0张的场合，你的牌组洗切把最上面的牌加入生命护甲。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.ELDORA).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Request");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            draw(2);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                if(playerChoiceMode() == 1)
                {
                    if(addToHand(CardLocation.LIFE_CLOTH))
                    {
                        CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().fromHand()).get();
                        addToLifeCloth(cardIndex);
                    }
                } else {
                    if(getLifeClothCount(getOwner()) == 0)
                    {
                        shuffleDeck();
                        addToLifeCloth(1);
                    }
                }
            }
        }
    }
}
