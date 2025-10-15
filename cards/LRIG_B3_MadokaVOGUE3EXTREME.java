package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_B3_MadokaVOGUE3EXTREME extends Card {

    public LRIG_B3_MadokaVOGUE3EXTREME()
    {
        setImageSets("SPDi43-15", "SPDi43-15P");

        setOriginalName("VOGUE3-EXTREME マドカ");
        setAltNames("ボーグスリーエクストリームマドカ Boogu Surii Ekusutoriimu Madoka");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から青のカードを２枚捨ててもよい。そうした場合、それをバニッシュする。\n" +
                "@A $G1 @[@|ブレイキング|@]@ %B0：あなたのルリグトラッシュから&E@0を持たないコストの合計が３以下の青のアーツ１枚を対象とし、それをルリグデッキに加える。"
        );

        setName("en", "Madoka, VOGUE 3-EXTREME");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 2 blue cards from your hand. If you do, banish it.\n" +
                "@A $G1 @[@|Breaking|@]@ %B0: Target 1 blue ARTS with a total cost of 3 or less and without &E@0 from your trash, and return it to the LRIG deck."
        );

		setName("zh_simplified", "VOGUE3-EXTREME 円");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把蓝色的牌2张舍弃。这样做的场合，将其破坏。\n" +
                "@A $G1 :霹雳%B0从你的分身废弃区把不持有&E]的费用的合计在3以下的蓝色的必杀1张作为对象，将其加入分身牌组。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Breaking");
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().withColor(CardColor.BLUE)).size() == 2)
            {
                banish(target);
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().ARTS().withColor(CardColor.BLUE).withCost(0,3).
                not(new TargetFilter().withDescription("&E")).fromTrash(DeckType.LRIG)
            ).get();
            returnToDeck(target, DeckPosition.TOP);
        }
    }
}
