package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_R1_LOVITMemoriaPhantomBeast extends Card {

    public SIGNI_R1_LOVITMemoriaPhantomBeast()
    {
        setImageSets("WXDi-P09-058", "WXDi-P09-058P", "SPDi01-92");

        setOriginalName("幻獣　LOVIT//メモリア");
        setAltNames("ゲンジュウラビットメモリア Genjuu Rabitto Memoria");
        setDescription("jp",
                "@C：このシグニは覚醒状態であるかぎり、@>@U：あなたのターン終了時、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。@@を得る。\n" +
                "@U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニは覚醒する。"
        );

        setName("en", "LOVIT//Memoria, Phantom Beast");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gains@>@U: At the end of your turn, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash.@@" +
                "@U: When this SIGNI vanishes a SIGNI on your opponent's field through battle, it is awakened. "
        );
        
        setName("en_fan", "LOVIT//Memoria, Phantom Beast");
        setDescription("en_fan",
                "@C: As long as this SIGNI is awakened, this SIGNI gains:" +
                "@>@U: At the end of your turn, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash.@@" +
                "@U: Whenever this SIGNI banishes your opponent's SIGNI in battle, this SIGNI awakens."
        );

		setName("zh_simplified", "幻兽 LOVIT//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，得到\n" +
                "@>@U :你的回合结束时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。@@\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
            trash(target);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
    }
}
