package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_AromaKurosuPriParaIdol extends Card {

    public SIGNI_R3_AromaKurosuPriParaIdol()
    {
        setImageSets("WXDi-P10-056");

        setOriginalName("プリパラアイドル　黒須あろま");
        setAltNames("プリパラアイドルクロスアロマ Puripara Aidoru Kurosu Aroma");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー10000以下のシグニ１体を対象とし、あなたのエナゾーンから＜プリパラ＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Kurosu Aroma, Pripara Idol");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put a <<Pripara>> SIGNI from your Ener Zone into your trash. If you do, vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Aroma Kurosu, PriPara Idol");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 10000 or less, and you may put 1 <<PriPara>> SIGNI from your ener zone into the trash. If you do, banish it."
        );

		setName("zh_simplified", "美妙天堂偶像 黑须茱香");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量10000以下的精灵1只作为对象，可以从你的能量区把<<プリパラ>>精灵1张放置到废弃区。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PRIPARA);
        setLevel(3);
        setPower(10000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromEner()).get();
                if(trash(cardIndex)) banish(target);
            }
        }
    }
}
