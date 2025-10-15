package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K1_Code2434RatnaPetit extends Card {
    
    public SIGNI_K1_Code2434RatnaPetit()
    {
        setImageSets("WXDi-P00-073");
        
        setOriginalName("コード２４３４　ラトナ・プティ");
        setAltNames("コードニジサンジラトナプティ");
        setDescription("jp",
                "@A #D：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－１０００する。あなたの場に＜バーチャル＞のシグニが３体以上ある場合、代わりにターン終了時まで、それのパワーを－２０００する。"
        );
        
        setName("en", "Ratna Petit, Code 2434");
        setDescription("en",
                "@A #D: Target SIGNI on your opponent's field gets --1000 power until end of turn. If there are three or more <<Virtual>> SIGNI on your field, it gets --2000 power until end of turn instead."
        );
        
        setName("en_fan", "Code 2434 Ratna Petit");
        setDescription("en_fan",
                "@A #D: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power. If there are 3 or more <<Virtual>> SIGNI on your field, instead until end of turn, it gets --2000 power."
        );
        
		setName("zh_simplified", "2434代号 拉特娜·葡蒂");
        setDescription("zh_simplified", 
                "@A #D:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-1000。你的场上的<<バーチャル>>精灵在3只以上的场合，作为替代，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 3 ? -2000 : -1000, ChronoDuration.turnEnd());
        }
    }
}
