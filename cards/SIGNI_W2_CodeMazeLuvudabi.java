package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_CodeMazeLuvudabi extends Card {
    
    public SIGNI_W2_CodeMazeLuvudabi()
    {
        setImageSets("WXDi-P05-049");
        
        setOriginalName("コードメイズ　ルヴダビ");
        setAltNames("コードメイズルヴダビ Koodo Meizu Rubudabi");
        setDescription("jp",
                "@C：このシグニのパワーはあなたの場にいる白のルリグ１体につき＋2000される。" +
                "~#：あなたの場に白のルリグが２体以上いる場合、対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Luvdabi, Code: Maze");
        setDescription("en",
                "@C: This SIGNI gets +2000 power for each white LRIG on your field." +
                "~#If you have two or more white LRIG on your field, return target SIGNI with power 10000 or less on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Code Maze Luvudabi");
        setDescription("en_fan",
                "@C: This SIGNI gets +2000 power for each white LRIG on your field." +
                "~#If there are 2 or more white LRIGs on your field, target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );
        
		setName("zh_simplified", "迷宫代号 阿布扎比卢浮宫");
        setDescription("zh_simplified", 
                "@C :这只精灵的力量依据你的场上的白色的分身的数量，每有1只就+2000。" +
                "~#你的场上的白色的分身在2只以上的场合，对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 2000 * new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount();
        }
        
        private void onLifeBurstEff()
        {
            if(new TargetFilter().own().anyLRIG().withColor(CardColor.WHITE).getValidTargetsCount() >= 2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
                addToHand(target);
            }
        }
    }
}
