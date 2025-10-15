package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_W1_CodeMazeTotorisa extends Card {
    
    public SIGNI_W1_CodeMazeTotorisa()
    {
        setImageSets("WXDi-P02-049");
        
        setOriginalName("コードメイズ　トトリサ");
        setAltNames("コードメイズトトリサ Koodo Meizu Totorisa");
        setDescription("jp",
                "@U $T1：対戦相手のシグニ１体が場からトラッシュに置かれたとき、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Totorisa, Code: Maze");
        setDescription("en",
                "@U $T1: When a SIGNI on your opponent's field is put into the trash, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Code Maze Totorisa");
        setDescription("en_fan",
                "@U $T1: When 1 of your opponent's SIGNI is put from the field into the trash, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "迷宫代号 鸟取砂丘");
        setDescription("zh_simplified", 
                "@U $T1 :当对战对手的精灵1只从场上放置到废弃区时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            enerCharge(1);
        }
    }
}
