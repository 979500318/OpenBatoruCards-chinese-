package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.ExceedCost;

public final class LRIG_B3_AllosPirulukKilolitre extends Card {

    public LRIG_B3_AllosPirulukKilolitre()
    {
        setImageSets("WXDi-P14-007", "WXDi-P14-007U");
        setLinkedImageSets("WXDi-P14-TK01","WXDi-P14-TK02","WXDi-P14-TK03","WXDi-P14-TK04","WXDi-P14-TK05");

        setOriginalName("アロス・ピルルク　kℓ");
        setAltNames("アロスピルルクキロリットル Arosu Piruruku Kirorittaru kl");
        setDescription("jp",
                "@U $T1：あなたのターンの間、コストか効果によってあなたが手札を１枚捨てたとき、カードを１枚引く。\n" +
                "@A $T1 @[手札を３枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。\n" +
                "@A @[エクシード４]@：フェゾーネマジックのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Allos Piruluk kℓ");
        setDescription("en",
                "@U $T1: During your turn, when you discard a card by a cost or an effect, draw a card.\n@A $T1 @[Discard three cards]@: Target SIGNI on your opponent's field gets --10000 power until end of turn.\n@A @[Exceed 4]@: Reveal two different Fesonne Magic Craft and add them to your LRIG Deck. "
        );
        
        setName("en_fan", "Allos Piruluk Kilolitre");
        setDescription("en_fan",
                "@U $T1: During your turn, when you discard 1 card from your hand by a cost or an effect, draw 1 card.\n" +
                "@A $T1 @[Discard 3 cards from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power.\n" +
                "@A @[Exceed 4]@: Reveal 2 different Fessone Magic crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "阿洛斯·皮璐璐可　kl");
        setDescription("zh_simplified", 
                "@U $T1 :你的回合期间，当因为费用或效果你把手牌1张舍弃时，抽1张牌。\n" +
                "@A $T1 手牌3张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n" +
                "@A @[超越 4]@:从音乐节魔术的衍生把2种类各1张公开加入分身牌组。（音乐节魔术有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
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

            AutoAbility auto = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act1 = registerActionAbility(new DiscardCost(3), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) && getEvent().getSourceAbility() != null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            playerChoiceFessoneMagic();
        }
    }
}
