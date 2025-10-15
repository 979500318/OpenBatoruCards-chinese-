package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_W_UtullsTerritory extends Card {

    public SPELL_W_UtullsTerritory()
    {
        setImageSets("WXDi-P11-056");
        setLinkedImageSets("WXDi-P11-050");

        setOriginalName("ウトゥルス・テリトリー");
        setAltNames("ウトゥルステリトリー Uturusu Teritorii Utulls");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュからレベル２以下の＜天使＞のシグニとレベル２以下の＜古代兵器＞のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。\n" +
                "$$2あなたの場に《融合せし極門　ウトゥルス//メモリア》がある場合、対戦相手のシグニ１体を対象とし、それを手札に戻す。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Ut'ulls Territory");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Add up to one target level two or less <<Angel>> SIGNI and one target level two or less <<Legacy>> SIGNI from your trash to your hand.\n" +
                "$$2 If \"Ut'ulls//Memoria, Fusion Ultra Gate\" is on your field, return target SIGNI on your opponent's field to its owner's hand." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Ut'ulls Territory");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 1 level 2 or lower <<Angel>> SIGNI and up to 1 level 2 or lower <<Ancient Weapon>> SIGNI from your trash, and add them to your hand.\n" +
                "$$2 If there is \"Ut'ulls//Memoria, Fused Ultimate Gate\" on your field, target 1 of your opponent's SIGNI, and return it to their hand." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "乌托鲁斯·领地");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的废弃区把等级2以下的<<天使>>精灵和等级2以下的<<古代兵器>>精灵各1张最多作为对象，将这些加入手牌。\n" +
                "$$2 你的场上有《融合せし極門　ウトゥルス//メモリア》的场合，对战对手的精灵1只作为对象，将其返回手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE,1) + Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setModeChoice(1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANGEL).withLevel(0,2).fromTrash());
                data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).withLevel(0,2).fromTrash()).get());
                spell.setTargets(data);
            } else if(new TargetFilter().own().SIGNI().withName("融合せし極門　ウトゥルス//メモリア").getValidTargetsCount() > 0)
            {
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()));
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1 || new TargetFilter().own().SIGNI().withName("融合せし極門　ウトゥルス//メモリア").getValidTargetsCount() > 0)
            {
                addToHand(spell.getTargets());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
