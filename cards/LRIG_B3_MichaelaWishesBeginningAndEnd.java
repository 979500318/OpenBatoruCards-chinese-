package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIG_B3_MichaelaWishesBeginningAndEnd extends Card {

    public LRIG_B3_MichaelaWishesBeginningAndEnd()
    {
        setImageSets("WXDi-P16-011", "WXDi-P16-011U");
        setLinkedImageSets("WXDi-P16-TK01");

        setOriginalName("希望の後先　ミカエラ");
        setAltNames("キボウノアトサキミカエラ Kibou no Atosaki Mikaera");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置く。\n" +
                "@E: カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $G1 %B0：クラフトの《インビンシブル・ストーリー》１枚をルリグデッキに加える。対戦相手の手札が２枚以下の場合、このターン、そのピースの使用コストは%X減る。"
        );

        setName("en", "Michaela, Beginning and End of Hope");
        setDescription("en",
                "@U: Whenever this LRIG attacks, put target SIGNI on your opponent's field on the bottom of its owner's deck.\n@E: Draw a card and [[Ener Charge 1]].\n@A $G1 %B0: Add an \"Invincible Story\" Craft to your LRIG Deck. If your opponent has two or fewer cards in their hand, the use cost of that PIECE is reduced by %X this turn."
        );
        
        setName("en_fan", "Michaela, Wishes' Beginning and End");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, target 1 of your opponent's SIGNI, and put it on the bottom of their deck.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 %B0: Add 1 \"Invincible Story\" craft into your LRIG deck. If your opponent has 2 or less cards in their hand, this turn, its use cost is reduced by %X."
        );

		setName("zh_simplified", "希望的后先 米卡伊来");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，对战对手的精灵1只作为对象，将其放置到牌组最下面。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %B0:衍生的《インビンシブル・ストーリー》1张加入分身牌组。对战对手的手牌在2张以下的场合，这个回合，那张和音的使用费用减%X。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }

        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }

        private void onActionEff()
        {
            CardIndex cardIndex = craft(getLinkedImageSets().get(0));
            if(returnToDeck(cardIndex, DeckPosition.TOP) && getHandCount(getOpponent()) <= 2)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLocation().match(cardIndex),
                    new CostModifier(() -> new EnerCost(Cost.colorless(1)), ModifierMode.REDUCE)
                );
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
    }
}
