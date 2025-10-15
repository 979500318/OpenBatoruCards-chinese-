package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_CodeRideRickshawDissona extends Card {

    public SIGNI_R3_CodeRideRickshawDissona()
    {
        setImageSets("WXDi-P12-068");

        setOriginalName("コードライド　ジンリキ//ディソナ");
        setAltNames("コードライドジンリキディソナ Koodo Raido Jinriki Disona");
        setDescription("jp",
                "=R あなたの#Sのシグニ１体の上に置く\n\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニの下にあるシグニ１枚と同じレベルの対戦相手のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Rickshaw//Dissona, Code: Ride");
        setDescription("en",
                "=R Place on top of a #S SIGNI on your field. \n@U: At the beginning of your attack phase, you may pay %R. If you do, vanish target SIGNI on your opponent's field with the same level as the SIGNI underneath this SIGNI."
        );
        
        setName("en_fan", "Code Ride Rickshaw//Dissona");
        setDescription("en_fan",
                "=R Put on 1 of your #S @[Dissona]@ SIGNI\n\n" +
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with the same level as a SIGNI under this SIGNI, and you may pay %R. If you do, banish it."
        );

		setName("zh_simplified", "骑乘代号 人力车//失调");
        setDescription("zh_simplified", 
                "=R在你的#S的精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@U :你的攻击阶段开始时，与这只精灵的下面的精灵1张相同等级的对战对手的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().dissona());
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        
        private int cacheBitsLevels;
        private DataTable<CardIndex> cacheDataOP;
        private void onAutoEff(CardIndex caller)
        {
            cacheBitsLevels = 0;
            forEachCardUnder(getCardIndex(), cardIndex -> {
                if(cardIndex.getUnderType().getUnderCategory() == CardUnderCategory.UNDER &&
                   cardIndex.getIndexedInstance() != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    cacheBitsLevels |= 1<<cardIndex.getIndexedInstance().getLevelByRef();
                }
            });
            cacheDataOP = new DataTable<>();
            forEachSIGNIOnField(getOpponent(), cardIndex -> {
                if((1<<cardIndex.getIndexedInstance().getLevel().getValue() & cacheBitsLevels) != 0)
                {
                    cacheDataOP.add(cardIndex);
                }
            });
            
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI().match(cacheDataOP)).get();
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
