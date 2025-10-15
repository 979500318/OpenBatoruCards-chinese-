package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K2_SenberoWickedDevil extends Card {
    
    public SIGNI_K2_SenberoWickedDevil()
    {
        setImageSets("WXDi-P08-078");
        
        setOriginalName("凶魔　センベロ");
        setAltNames("キョウマセンベロ Kyouma Senbero");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場からトラッシュに置かれたとき、【エナチャージ１】をする。"
        );
        
        setName("en", "Senbero, Doomed Evil");
        setDescription("en",
                "@U: When this SIGNI is put into the trash from the field by a cost or an effect, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Senbero, Wicked Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is put from the field into the trash by a cost or effect, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "凶魔 超值畅饮");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && caller.isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            enerCharge(1);
        }
    }
}
