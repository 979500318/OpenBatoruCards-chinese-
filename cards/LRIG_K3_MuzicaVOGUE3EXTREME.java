package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class LRIG_K3_MuzicaVOGUE3EXTREME extends Card {

    public LRIG_K3_MuzicaVOGUE3EXTREME()
    {
        setImageSets("SPDi43-14", "SPDi43-14P");

        setOriginalName("VOGUE3-EXTREME ムジカ");
        setAltNames("ボーグスリーエクストリームムジカ Boogu Surii Ekusutoriimu Mujika");
        setDescription("jp",
                "@C：あなたのアタックフェイズの間、対戦相手の中央のシグニゾーンにあるシグニのパワーを－5000する。\n" +
                "@A $G1 @[@|ブレイキング|@]@ %K0：あなたのルリグトラッシュから&E@0を持たないコストの合計が３以下の黒のアーツ１枚を対象とし、それをルリグデッキに加える。"
        );

        setName("en", "Muzica, VOGUE 3-EXTREME");
        setDescription("en",
                "@C: During your attack phase, the SIGNI in your opponent's center SIGNI zone gets --5000 power.\n" +
                "@A $G1 @[@|Breaking|@]@ %K0: Target 1 black ARTS with a total cost of 3 or less and without &E@0 from your trash, and return it to the LRIG deck."
        );

		setName("zh_simplified", "VOGUE3-EXTREME 穆希卡");
        setDescription("zh_simplified", 
                "@C :你的攻击阶段期间，对战对手的中央的精灵区的精灵的力量-5000。\n" +
                "@A $G1 :霹雳%K0从你的分身废弃区把不持有&E]的费用的合计在3以下的黑色的必杀1张作为对象，将其加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MUZICA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().OP().SIGNI().fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(-5000));

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Breaking");
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() && GamePhase.isAttackPhase(getCurrentPhase()) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().ARTS().withColor(CardColor.BLACK).withCost(0,3).
                not(new TargetFilter().withDescription("&E")).fromTrash(DeckType.LRIG)
            ).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
