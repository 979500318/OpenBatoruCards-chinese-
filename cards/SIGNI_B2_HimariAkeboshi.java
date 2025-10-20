package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;
import open.batoru.data.CardDataImageSet.Mask;

public final class SIGNI_B2_HimariAkeboshi extends Card {

    public SIGNI_B2_HimariAkeboshi()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-079");

        setOriginalName("明星ヒマリ");
        setAltNames("アケボシヒマリ Akeboshi Himari");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他の＜ブルアカ＞のシグニ１体を対象とし、アップ状態のこのシグニをダウンしてもよい。そうした場合、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手は手札を１枚捨てる。@@を得る。" +
                "~{{C：このシグニのパワーは＋4000される。@@" +
                "~#：対戦相手のルリグかシグニ１体を対象とする。このターン、それがアタックしたとき、対戦相手が手札を３枚捨てないかぎり、そのアタックを無効にする。"
        );

        setName("en", "Akeboshi Himari");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may down this upped SIGNI. If you do, another target <<Blue Archive>> SIGNI on your field gains@>@U: Whenever this SIGNI attacks, your opponent discards a card.@@until end of turn. ~{{C: This SIGNI gets +4000 power.@@" +
                "~#Whenever target LRIG or SIGNI on your opponent's field attacks this turn, negate that attack unless your opponent discards three cards."
        );
        
        setName("en_fan", "Himari Akeboshi");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your other <<Blue Archive>> SIGNI, and you may down this upped SIGNI. If you do, until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, your opponent discards 1 card from their hand.@@" +
                "~{{C: This SIGNI gets +4000 power.@@" +
                "~#Target 1 of your opponent's LRIG or SIGNI. This turn, whenever it attacks, disable that attack unless your opponent discards 3 cards from their hand."
        );

		setName("zh_simplified", "明星日鞠");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的<<ブルアカ>>精灵1只作为对象，可以把竖直状态的这只精灵横置。这样做的场合，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手把手牌1张舍弃。@@\n" +
                "~{{C:这只精灵的力量+4000。@@" +
                "~#对战对手的分身或精灵1只作为对象。这个回合，当其攻击时，如果对战对手不把手牌3张舍弃，那么那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(getCardIndex())).get();
            
            if(target != null && !isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down())
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            discard(getOpponent(), 1);
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
