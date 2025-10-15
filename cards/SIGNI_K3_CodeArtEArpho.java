package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_CodeArtEArpho extends Card {
    
    public SIGNI_K3_CodeArtEArpho()
    {
        setImageSets("WXDi-P02-088");
        
        setOriginalName("コードアート　Eヤーホ");
        setAltNames("コードアートイーヤーホ Koodo Aato Ii Yaaho");
        setDescription("jp",
                "@U $T1：いずれかのプレイヤーがスペルを使用したとき、[[エナチャージ１]]をする。\n" +
                "@E %K %K：あなたのトラッシュから黒のスペル１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "E - Fone, Code: Art");
        setDescription("en",
                "@U $T1: When a player uses a spell, [[Ener Charge 1]].\n" +
                "@E %K %K: Add target black spell from your trash to your hand."
        );
        
        setName("en_fan", "Code Art E Arpho");
        setDescription("en_fan",
                "@U $T1: When either player uses a spell, [[Ener Charge 1]].\n" +
                "@E %K %K: Target 1 black spell from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "必杀代号 耳机");
        setDescription("zh_simplified", 
                "@U $T1 :当任一方的玩家把魔法使用时，[[能量填充1]]。\n" +
                "@E %K %K:从你的废弃区把黑色的魔法1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.USE_SPELL, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 2)), this::onEnterEff);
        }
        
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
    }
}
