package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_ShokudaikiriMediumEquipment extends Card {
    
    public SIGNI_R2_ShokudaikiriMediumEquipment()
    {
        setImageSets("WXDi-P01-058");
        
        setOriginalName("中装　ショクダイキリ");
        setAltNames("チュウソウショクダイキリ Chuusou Shokudaikiri");
        setDescription("jp",
                "@E %X：あなたのレベル３のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Shokudai - kiri, High Armed");
        setDescription("en",
                "@E %X: Target level three SIGNI on your field gains@>@U: Whenever this SIGNI attacks, vanish target SIGNI on your opponent's field with power 8000 or less@@until end of turn."
        );
        
        setName("en_fan", "Shokudaikiri, Medium Equipment");
        setDescription("en_fan",
                "@E %X: Target 1 of your level 3 SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "中装 烛台切光忠");
        setDescription("zh_simplified", 
                "@E %X:你的等级3的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量8000以下的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(3)).get();
            if(target != null)
            {
                AutoAbility abilityAttached = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                
                attachAbility(target, abilityAttached, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
