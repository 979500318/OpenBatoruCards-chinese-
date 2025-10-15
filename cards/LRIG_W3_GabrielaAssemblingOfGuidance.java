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
import open.batoru.data.ability.cost.AbilityCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIG_W3_GabrielaAssemblingOfGuidance extends Card {

    public LRIG_W3_GabrielaAssemblingOfGuidance()
    {
        setImageSets("WXDi-P16-009", "WXDi-P16-009U");
        setLinkedImageSets("WXDi-P16-TK01");

        setOriginalName("導きを集う　ガブリエラ");
        setAltNames("ミチビキヲツドウガブリエラ Michibi Wo Tsudou Gaburiera");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋10000する。\n" +
                "@E: カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $G1 %W0：クラフトの《インビンシブル・ストーリー》１枚をルリグデッキに加える。あなたのライフクロスが２枚以下の場合、このターン、そのピースの使用コストは%X減る。"
        );

        setName("en", "Gabriela, Guidance Gatherer");
        setDescription("en",
                "@U: Whenever this LRIG attacks, target SIGNI on your field gets +10000 power until the end of your opponent's next end phase.\n@E: Draw a card and [[Ener Charge 1]].\n@A $G1 %W0: Add an \"Invincible Story\" Craft to your LRIG Deck. If you have two or fewer cards in your Life Cloth, the use cost of that PIECE is reduced by %X this turn."
        );
        
        setName("en_fan", "Gabriela, Assembling of Guidance");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, target 1 of your SIGNI, and until the end of your opponent's next turn, it gets +10000 power.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 %W0: Add 1 \"Invincible Story\" craft into your LRIG deck. If you have 2 or less life cloth, this turn, its use cost is reduced by %X."
        );

		setName("zh_simplified", "聚集的指引 哲布伊来");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+10000。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %W0:衍生的《インビンシブル・ストーリー》1张加入分身牌组。你的生命护甲在2张以下的场合，这个回合，那张和音的使用费用减%X。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 10000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }

        private void onActionEff()
        {
            CardIndex cardIndex = craft(getLinkedImageSets().get(0));
            if(returnToDeck(cardIndex, DeckPosition.TOP) && getLifeClothCount(getOwner()) <= 2)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLocation().match(cardIndex),
                    new CostModifier(() -> new EnerCost(Cost.colorless(1)), ModifierMode.REDUCE)
                );
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
    }
}
