package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_GaapWickedAngel extends Card {
    
    public SIGNI_K2_GaapWickedAngel()
    {
        setImageSets("WXDi-D06-014");
        
        setOriginalName("凶天　ガープ");
        setAltNames("キョウテンガープ Kyouten Gappu");
        setDescription("jp",
                "@E %X：あなたのトラッシュから白か赤か青か緑のレベル１のシグニ１枚を対象とし、それを場に出す。" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Gaap, Doomed Angel");
        setDescription("en",
                "@E %X: Put target white, red, blue, or green level one SIGNI from your trash onto your field." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Gaap, Wicked Angel");
        setDescription("en_fan",
                "@E %X: Target 1 white, red, blue or green level 1 SIGNI from your trash, and put it onto the field." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "凶天 嘉波");
        setDescription("zh_simplified", 
                "@E %X从你的废弃区把白色或红色或蓝色或绿色的等级1的精灵1张作为对象，将其出场。（持有%Wi或%Ri或%Bi或%Gi的精灵出场）" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).withColor(CardColor.WHITE,CardColor.RED,CardColor.BLUE,CardColor.GREEN).playable().fromTrash()).get();
            putOnField(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
