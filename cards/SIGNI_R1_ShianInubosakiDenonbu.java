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

public final class SIGNI_R1_ShianInubosakiDenonbu extends Card {

    public SIGNI_R1_ShianInubosakiDenonbu()
    {
        setImageSets("WXDi-P14-083");

        setOriginalName("電音部　犬吠埼紫杏");
        setAltNames("デンオンブイヌボウサキシアン Denonbu Inubosaki Shian");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー5000以下のシグニ１体を対象とし、手札から＜電音部＞のシグニを１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "DEN-ON-BU Shian Inubousaki");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard a <<DEN-ON-BU>> SIGNI. If you do, vanish target SIGNI on your opponent's field with power 5000 or less."
        );
        
        setName("en_fan", "Shian Inubosaki, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 5000 or less, and you may discard 1 <<Denonbu>> SIGNI from your hand. If you do, banish it."
        );

		setName("zh_simplified", "电音部 犬吠埼紫杏");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量5000以下的精灵1只作为对象，可以从手牌把<<電音部>>精灵1张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(1);
        setPower(2000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            
            if(target != null && discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU)).get() != null)
            {
                banish(target);
            }
        }
    }
}
