package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R2_HinaMinakamiDenonbu extends Card {

    public SIGNI_R2_HinaMinakamiDenonbu()
    {
        setImageSets("WXDi-P14-081");

        setOriginalName("電音部　水上雛");
        setAltNames("デンオンブミナカミヒナ Denonbu Minakami Hina");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、手札から＜電音部＞のシグニを１枚捨ててもよい。そうした場合、【エナチャージ１】をする。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "DEN-ON-BU Hina Minakami");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a <<DEN-ON-BU>> SIGNI. If you do, [[Ener Charge 1]]." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Hina Minakami, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may discard 1 <<Denonbu>> SIGNI from your hand. If you do, [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may discard 1 card from your hand. If you do, banish it."
        );

		setName("zh_simplified", "电音部 水上雏");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以从手牌把<<電音部>>精灵1张舍弃。这样做的场合，[[能量填充1]]。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU)).get() != null)
            {
                enerCharge(1);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
