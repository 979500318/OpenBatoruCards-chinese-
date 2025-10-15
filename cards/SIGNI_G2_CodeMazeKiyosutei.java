package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_CodeMazeKiyosutei extends Card {
    
    public SIGNI_G2_CodeMazeKiyosutei()
    {
        setImageSets("WXDi-P01-075", "SPDi38-20");
        
        setOriginalName("コードメイズ　キヨステイ");
        setAltNames("コードメイズキヨステイ Koodo Meizu Kiyosutei");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋4000される。\n" +
                "@E：対戦相手の場にあるすべてのシグニを好きなように配置し直す。" +
                "~#：[[エナチャージ１]]をする。このターン、次にシグニがアタックしたとき、そのアタックを無効にする。"
        );
        
        setName("en", "Kiyosumi, Code: Maze");
        setDescription("en",
                "@C: As long as this SIGNI is in the center SIGNI Zone, it gets +4000 power.\n" +
                "@E: You may rearrange the positions of all SIGNI on your opponent's field." +
                "~#[[Ener Charge 1]]. When the next SIGNI attacks this turn, negate the attack."
        );
        
        setName("en_fan", "Code Maze Kiyosutei");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on the center SIGNI zone, this SIGNI gets +4000 power.\n" +
                "@E: Rearrange all of your opponent's SIGNI on the field as you like." +
                "~#[[Ener Charge 1]]. This turn, the next time a SIGNI attacks, disable that attack."
        );
        
		setName("zh_simplified", "迷宫代号 清澄庭园");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+4000。\n" +
                "@E :对战对手的场上的全部的精灵任意重新配置。" +
                "~#[[能量填充1]]。这个回合，当下一次精灵攻击时，那次攻击无效。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            rearrangeAll(getOpponent());
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_ATTACKED, getOwner(), record, data -> {
                if(!CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType())) return RuleCheckState.IGNORE;
                
                record.forceExpire();
                
                return RuleCheckState.BLOCK;
            });
        }
    }
}
