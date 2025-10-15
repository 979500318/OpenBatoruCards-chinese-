package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_WOLFDissonaExplosiveGun extends Card {

    public SIGNI_G2_WOLFDissonaExplosiveGun()
    {
        setImageSets("WXDi-P13-079");

        setOriginalName("爆砲　WOLF//ディソナ");
        setAltNames("バクホウウルフディソナ Bakuhou Urufu Disona");
        setDescription("jp",
                "@E @[エナゾーンから#Sのカード２枚をトラッシュに置く]@：ターン終了時まで、このシグニは@>@C：このシグニは正面のシグニがレベル２以下であるかぎり、【ランサー】を得る。@@を得る。"
        );

        setName("en", "WOLF//Dissona, Erupting Cannon");
        setDescription("en",
                "@E @[Put two #S cards from your Ener Zone into your trash]@: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI is level two or less, this SIGNI gains [[Lancer]].@@until end of turn."
        );
        
        setName("en_fan", "WOLF//Dissona, Explosive Gun");
        setDescription("en_fan",
                "@E @[Put 2 #S @[Dissona]@ cards from your ener zone into the trash]@: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI is level 2 or lower, this SIGNI gains [[Lancer]]."
        );

		setName("zh_simplified", "爆炮 WOLF//失调");
        setDescription("zh_simplified", 
                "@E 从能量区把#S的牌2张放置到废弃区:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵在等级2以下时，得到[[枪兵]]。@@\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
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
            
            registerEnterAbility(new TrashCost(2, new TargetFilter().dissona().fromEner()), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getOppositeSIGNI() != null && getOppositeSIGNI().getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }
    }
}
