package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R1_LOVITFessonePhantomBeast extends Card {

    public SIGNI_R1_LOVITFessonePhantomBeast()
    {
        setImageSets("WXDi-P14-054");

        setOriginalName("幻獣　LOVIT//フェゾーネ");
        setAltNames("ゲンジュウラビットフェゾーネ Genjuu Rabitto Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にレベル３の覚醒状態のシグニがある場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、それをトラッシュに置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "LOVIT//Fesonne, Phantom Beast");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a level three awakened SIGNI on your field, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "LOVIT//Fessone, Phantom Beast");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is a level 3 awakened SIGNI on your field, target 1 card that doesn't share a common color with your opponent's center LRIG from your opponent's ener zone, and put it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "幻兽 LOVIT//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有等级3的觉醒状态的精灵的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withState(CardStateFlag.AWAKENED).withLevel(3).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner()).get();
                trash(target);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
