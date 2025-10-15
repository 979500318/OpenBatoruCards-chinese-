package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_K2_CaesarWickedGeneral extends Card {
    
    public SIGNI_K2_CaesarWickedGeneral()
    {
        setImageSets("WXDi-P05-081", "SPDi01-69");
        
        setOriginalName("凶将　カエサル");
        setAltNames("キョウショウカエサル Kyoushou Kaesaru");
        setDescription("jp",
                "@E %K：あなたのトラッシュにカードが７枚以上ある場合、ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニのパワーが5000以下であるかぎり、[[アサシン]]を得る。@@を得る。"
        );
        
        setName("en", "Caesar, Wicked General");
        setDescription("en",
                "@E %K: If you have seven or more cards in your trash, this SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI has power 5000 or less, this SIGNI gains [[Assassin]].@@until end of turn."
        );
        
        setName("en_fan", "Caesar, Wicked General");
        setDescription("en_fan",
                "@E %K: If there are 7 or more cards in your trash, until end of turn, this SIGNI gains:" +
                "@>@C: As long as there is a SIGNI with power 5000 or less in front of this SIGNI, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "凶将 尤利乌斯恺撒");
        setDescription("zh_simplified", 
                "@E %K:你的废弃区的牌在7张以上的场合，直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在5000以下时，得到[[暗杀]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(getTrashCount(getOwner()) >= 7)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getOppositeSIGNI() != null && getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
    }
}
