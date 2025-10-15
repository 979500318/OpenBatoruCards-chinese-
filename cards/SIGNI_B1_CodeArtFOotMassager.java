package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_B1_CodeArtFOotMassager extends Card {

    public SIGNI_B1_CodeArtFOotMassager()
    {
        setImageSets("WX25-P2-082");

        setOriginalName("コードアート　Fットマッサージャー");
        setAltNames("コードアートエフットマッサージャー Koodo Aato Efutto Massaajaa Foot Massager");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札から＜電機＞のシグニを１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。この効果であなたの黒の＜電機＞のシグニを捨てた場合、代わりに対戦相手の手札を１枚見て選び、捨てさせてもよい。" +
                "~#：対戦相手のルリグ１体を対象とする。このターン、そのルリグでアタックできない。対戦相手が手札を３枚以下でないかぎり、そのアタックを無効にする。"
        );

        setName("en", "Code Art F Oot Massager");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard 1 <<Electric Machine>> SIGNI from your hand. If you do, your opponent discards 1 card from their hand. If you discarded a black <<Electric Machine>> SIGNI from your hand this way, instead choose 1 card from your opponent's hand without looking, and discard it." +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "必杀代号 足部按摩器");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从手牌把<<電機>>精灵1张舍弃。这样做的场合，对战对手把手牌1张舍弃。这个效果你把黑色的<<電機>>精灵舍弃的场合，作为替代，不看对战对手的手牌选1张，舍弃。" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE)).get();
            
            if(cardIndex != null)
            {
                if(!cardIndex.getIndexedInstance().getColor().matches(CardColor.BLACK))
                {
                    discard(getOpponent(), 1);
                } else {
                    discard(playerChoiceHand());
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().fromField()).get();

            if(target != null)
            {
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, new GFXTextureCardCanvas("border/discard", 0.75,3)));
                addCardRuleCheck(CardRuleCheckType.COST_TO_LAND_ATTACK, target, record, data -> new DiscardCost(0,3, ChoiceLogic.BOOLEAN));
            }
        }
    }
}
