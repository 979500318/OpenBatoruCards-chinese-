package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_G1_CodeMazeTochou extends Card {
    
    public SIGNI_G1_CodeMazeTochou()
    {
        setImageSets("WXDi-P05-071");
        
        setOriginalName("コードメイズ　トチョー");
        setAltNames("Koodo Meizu Tochoo");
        setDescription("jp",
                "@U：対戦相手のシグニが１体以上他のシグニゾーンに移動したとき、あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000する。\n" +
                "@E：対戦相手のシグニ２体を対象とし、それらの場所を入れ替える。" +
                "~#：[[エナチャージ１]]をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Tocho, Code: Maze");
        setDescription("en",
                "@U: Whenever one or more SIGNI on your opponent's field moves to a different SIGNI Zone, target SIGNI on your field gets +2000 power until end of turn.\n" +
                "@E: Swap the positions of two target SIGNI on your opponent's field." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Code Maze Tochou");
        setDescription("en_fan",
                "@U: Whenever 1 or more of your opponent's SIGNI are moved to another SIGNI zone, target 1 of your SIGNI, and until end of turn, it gets +2000 power.\n" +
                "@E: Target 2 of your opponent's SIGNI, and exchange their positions." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "迷宫代号 东京都厅");
        setDescription("zh_simplified", 
                "@U :当对战对手的精灵1只以上往其他的精灵区移动时，你的精灵1只作为对象，直到回合结束时为止，其的力量+2000。\n" +
                "@E :对战对手的精灵2只作为对象，将这些的场所交换。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().isAtOnce(1) &&
                   CardLocation.isSIGNI(caller.getLocation()) && CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 2000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.MOVE).OP().SIGNI());
            
            if(data.get() != null)
            {
                exchange(data.get(0), data.get(1));
            }
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
