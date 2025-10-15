package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G1_MandragoraVerdantDevil extends Card {

    public SIGNI_G1_MandragoraVerdantDevil()
    {
        setImageSets("WXDi-P10-065");
        
        setOriginalName("翠魔　マンドラゴラ");
        setAltNames("スイママンドラゴラ Suima Mandoragora");
        setDescription("jp",
                "@A $T1 %X @[＜悪魔＞のシグニ１体を場からトラッシュに置く]@：カードを１枚引く。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Mandragora, Jade Evil");
        setDescription("en",
                "@A $T1 %X @[Put a <<Demon>> SIGNI on your field into its owner's trash]@: Draw a card." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a SIGNI this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Mandragora, Verdant Devil");
        setDescription("en_fan",
                "@A $T1 %X @[Put 1 <<Devil>> SIGNI from your field into the trash]@: Draw 1 card." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );
        
		setName("zh_simplified", "翠魔 曼陀罗草");
        setDescription("zh_simplified", 
                "@A $T1 %X<<悪魔>>精灵1只从场上放置到废弃区:抽1张牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            ActionAbility act = registerActionAbility(new AbilityCostList(
                new EnerCost(Cost.colorless(1)),
                new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL)
            )), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            draw(1);
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isSIGNI(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
