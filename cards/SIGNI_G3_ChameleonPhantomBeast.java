package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.DataTable;
import open.batoru.data.ability.modifiers.PowerModifier;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_G3_ChameleonPhantomBeast extends Card {
    
    public SIGNI_G3_ChameleonPhantomBeast()
    {
        setImageSets("WXDi-P05-076");
        
        setOriginalName("幻獣　カメレオン");
        setAltNames("ゲンジュカメレオン Genjuu Kamereon");
        setDescription("jp",
                "@C：あなたの場にそれぞれ共通する色を持つルリグが２体いるかぎり、このシグニのパワーは＋2000される。３体いるかぎり、代わりにこのシグニのパワーは＋3000される。\n" +
                "@U：このシグニがアタックしたとき、あなたの場にパワー15000以上のシグニがある場合、[[エナチャージ１]]をする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ１]]"
        );
        
        setName("en", "Chameleon, Phantom Beast");
        setDescription("en",
                "@C: As long as there are two LRIG that share the same color on your field, this SIGNI gets +2000 power. If there are three LRIG that share the same color, instead this SIGNI gets +3000 power.\n" +
                "@U: Whenever this SIGNI attacks, if there is a SIGNI on your field with power 15000 or more, [[Ener Charge 1]]." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Chameleon, Phantom Beast");
        setDescription("en_fan",
                "@C: As long as there are 2 LRIG that share a common color on your field, this SIGNI gets +2000 power. As long as there are 3, it gets +3000 power instead.\n" +
                "@U: Whenever this SIGNI attacks, if there is a SIGNI with power 15000 or more on your field, [[Ener Charge 1]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "幻兽 变色龙");
        setDescription("zh_simplified", 
                "@C :你的场上的持有共通颜色的分身在2只时，这只精灵的力量+2000。3只时，作为替代，这只精灵的力量+3000。\n" +
                "@U :当这只精灵攻击时，你的场上有力量15000以上的精灵的场合，[[能量填充1]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerConstantAbility(new PowerModifier(this::onConstEffModGetValue));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            DataTable<CardIndex> data = getLRIGs(getOwner());
            if(data.size() < 2) return 0;
            
            Set<CardColor> cacheColors = new HashSet<>();
            int count = 1;
            for(int i=0;i<data.size();i++)
            {
                CardDataColor color = data.get(i).getIndexedInstance().getColor();
                if(color.matches(cacheColors)) count++;
                
                cacheColors.addAll(color.getValue());
            }
            
            return count == 2 ? 2000 : 3000;
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withPower(15000,0).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
