package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_DreiRikabuto extends Card {
    
    public SIGNI_K3_DreiRikabuto()
    {
        setImageSets("WXDi-D06-016");
        
        setOriginalName("ドライ＝リカブト");
        setAltNames("ドライリカブト Dorai Rikabuto");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、このシグニの正面にあったシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "@E %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にあるシグニが持つ色の種類１つにつき－3000する。"
        );
        
        setName("en", "Aconis Type: Drei");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may pay %X %X. If you do, the SIGNI that was in front of this SIGNI gets --10000 power until end of turn.\n" +
                "@E %K: Target SIGNI on your opponent's field gets --3000 power for each different color among SIGNI on your field until end of turn."
        );
        
        setName("en_fan", "Drei-Rikabuto");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, target 1 SIGNI that was in front of this SIGNI, and you may pay %X %X. If you do, until end of turn, that SIGNI gets --10000 power.\n" +
                "@E %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power for each color among SIGNI on your field."
        );
        
		setName("zh_simplified", "DREI=乌头毒");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，这只精灵的正面原有的精灵1只作为对象，可以支付%X %X。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@E %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的精灵的持有颜色的种类的数量，每有1种就-3000。（无色不含有颜色）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().fromLocation(CardLocation.getOppositeSIGNILocation(getEvent().getCaller().getLocation()))).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -3000 * CardAbilities.getColorsCount(getSIGNIOnField(getOwner())), ChronoDuration.turnEnd());
        }
    }
}
