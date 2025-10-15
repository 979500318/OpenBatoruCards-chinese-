package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W1_Code2434ChihiroYuki extends Card {
    
    public SIGNI_W1_Code2434ChihiroYuki()
    {
        setImageSets("WXDi-D02-20");
        
        setOriginalName("コード２４３４　勇気ちひろ");
        setAltNames("コードニジサンジユウキチヒロ Koodo Nijisanji Yuuki Chihiro");
        setDescription("jp",
                "@C：あなたの場に＜バーチャル＞のシグニが３体以上あるかぎり、このシグニのパワーは＋５０００される。"
        );
        
        setName("en", "Chihiro Yuki, Code 2434");
        setDescription("en",
                "@C: As long as there are three or more <<Virtual>> SIGNI on your field, this SIGNI gets +5000 power."
        );
        
        setName("en_fan", "Code 2434 Chihiro Yuki");
        setDescription("en_fan",
                "@C: As long as there are 3 or more <<Virtual>> SIGNI on your field, this SIGNI gets +5000 power."
        );
        
		setName("zh_simplified", "2434代号 勇气千寻");
        setDescription("zh_simplified", 
                "@C :你的场上的<<バーチャル>>精灵在3只以上时，这只精灵的力量+5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
